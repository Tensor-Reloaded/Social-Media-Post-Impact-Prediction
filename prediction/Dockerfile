FROM python:3.8.12-bullseye
COPY requirements.txt .
RUN pip install -r requirements.txt
COPY . .
EXPOSE 80
ENTRYPOINT python index.py
