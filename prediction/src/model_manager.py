from transformers import AutoModel, BertTokenizerFast
import torchvision
import torch
from src.model_architecture import HybridModel, BERT_Arch, get_decoder
from utils.image_preprocessing import preprocess_image
from utils.text_preprocessing import preprocess_text
import numpy as np

MODEL_PATH = "model3.h5"
MAX_SEQ_LEN = 22
NR_CLASSES = 10


class ModelManager:
    """Design Pattern user Adapter"""

    device = None
    model = None
    tokenizer = None

    @classmethod
    def change_model(cls, request_data):
        pass

    def get_one_model_prediction(cls, image, text):
        image = preprocess_image(image)
        text = preprocess_text(text)
        text_tokens = cls.tokenizer.tokenize(text)
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
        return np.argmax(prediction)

    @classmethod
    def _load_torch_model_from_aws_storage(cls):
        torch.load()

    @classmethod
    def initialize(cls, ):
        cls.device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
        decoder = get_decoder()
        bert = AutoModel.from_pretrained('bert-base-uncased')
        text_model = BERT_Arch(bert, NR_CLASSES)
        image_model = torchvision.models.resnet18(pretrained=True)
        num_ftrs = image_model.fc.in_features
        image_model.fc = torch.nn.Linear(num_ftrs, NR_CLASSES)
        cls.tokenizer = BertTokenizerFast.from_pretrained('bert-base-uncased')
        cls.model = HybridModel(image_model, text_model, decoder)
        cls.model.load_state_dict(cls._load_torch_model_from_aws_storage())
        cls.model.eval()
        cls.model.to(cls.device)
