import logging
from transformers import AutoModel, BertTokenizerFast
import torchvision
import torch
from src.model_architecture import HybridModel, BERT_Arch, get_decoder
from src.utils.image_preprocessing import preprocess_image
from src.utils.text_preprocessing import preprocess_text
import numpy as np
import s3fs
import os
import io

MODEL_PATH = "model3.h5"
MAX_SEQ_LEN = 22
NR_CLASSES = 10


def _postprocess_prediction(number):
    label_ranges = [0.0,
                    7.0,
                    15.0,
                    23.0,
                    39.0,
                    106.0,
                    441.0,
                    830.0,
                    2958.4000000000087,
                    17284.0,
                    1212284.0]
    return {
        "predictedNumberOfLikes": int((label_ranges[number]+label_ranges[number + 1])/2)
    }


class ModelManager:
    """Design Pattern user Adapter"""

    device = None
    model = None
    tokenizer = None

    @classmethod
    def change_model(cls, request_data):
        pass

    @classmethod
    def get_one_model_prediction(cls, image, text):
        with torch.no_grad():
            image = preprocess_image(image)
            text = preprocess_text(text)
            text_tokens = cls.tokenizer.encode_plus(text)
            seq = torch.tensor(text_tokens['input_ids'])
            mask = torch.tensor(text_tokens['attention_mask'])
            image = torch.from_numpy(image)
            seq.to(cls.device)
            image.to(cls.device)
            mask.to(cls.device)
            image = image / 255.0
            image_for_pred = image.permute([2, 1, 0])
            prediction = cls.model(image_for_pred.unsqueeze(0), seq.unsqueeze(0), mask.unsqueeze(0))
            prediction = prediction.cpu()
            prediction_class = np.argmax(prediction)
        return _postprocess_prediction(prediction_class)

    @classmethod
    def _load_torch_model_from_aws_storage(cls):
       logging.info(f"Mounting S3 bucket {os.environ['bucket']}")
       s3 = s3fs.S3FileSystem(anon=True)
       with s3.open(os.environ["bucket"] + "/model3.h5", "rb") as f:
           buffer = io.BytesIO(f.read())
       logging.info("Succesfully loaded model from S3")
       return torch.load(buffer, map_location=cls.device)
#       return torch.load("res/model3.h5", map_location=cls.device)

    @classmethod
    def initialize(cls):
        logging.info("Starting model initiation")
        cls.device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
        decoder = get_decoder()
        logging.info("Downloading BERT")
        bert = AutoModel.from_pretrained('bert-base-uncased')
        text_model = BERT_Arch(bert, NR_CLASSES)
        logging.info("Downloading Resnet")
        image_model = torchvision.models.resnet18(pretrained=True)
        num_ftrs = image_model.fc.in_features
        image_model.fc = torch.nn.Linear(num_ftrs, NR_CLASSES)
        logging.info("Downloading Tokenizer")
        cls.tokenizer = BertTokenizerFast.from_pretrained('bert-base-uncased')
        cls.model = HybridModel(image_model, text_model, decoder)
        cls.model.load_state_dict(cls._load_torch_model_from_aws_storage())
        logging.info("Loading Model from S3")
        cls.model.eval()
        cls.model.to(cls.device)

    @classmethod
    def is_model_loaded(cls):
        return cls.model is not None
