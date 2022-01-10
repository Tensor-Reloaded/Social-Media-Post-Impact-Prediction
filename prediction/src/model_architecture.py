from torch import nn


class BERT_Arch(nn.Module):

    def __init__(self, bert, classes_count):
        super(BERT_Arch, self).__init__()

        self.bert = bert

        # dropout layer
        self.dropout = nn.Dropout(0.1)

        # relu activation function
        self.relu = nn.ReLU()

        # dense layer 1
        self.fc1 = nn.Linear(768, 512)

        # dense layer 2 (Output layer)
        self.fc2 = nn.Linear(512, classes_count)

        # softmax activation function
        self.softmax = nn.LogSoftmax(dim=1)

    # define the forward pass
    def forward(self, sent_id, mask):
        # pass the inputs to the model
        _, cls_hs = self.bert(sent_id, attention_mask=mask, return_dict=False)

        x = self.fc1(cls_hs)

        x = self.relu(x)

        x = self.dropout(x)

        # output layer
        x = self.fc2(x)

        # apply softmax activation
        x = self.softmax(x)

        return x


class HybridModel(nn.Module):

    def __init__(self, image_model, text_model, decoder):
        super(HybridModel, self).__init__()
        self.image_model = image_model
        self.text_model = text_model
        self.decoder = decoder

    def forward(self, image_input, sent_id, mask):
        image_output = self.image_model(image_input)
        text_output = self.text_model(sent_id, mask)
        output = self.decoder(image_output, text_output)
        return output


def get_decoder():
    return lambda x, y: (x + y) / 2
