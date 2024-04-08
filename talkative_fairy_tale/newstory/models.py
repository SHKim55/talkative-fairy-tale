import datetime

from django.db import models
from datetime import datetime


class Story(models.Model):
    objects = models.Manager()                # ORM Manager

    title = models.CharField(max_length=255)  # static 변수 선언 == DB 컬럼 정의
    content = models.TextField(default="No Title")
    created_time = models.DateTimeField()

    def __init__(self, title, content):
        self.title = title
        self.content = content
        self.created_time = datetime.now()

    def __init__(self, content):
        self.content = content
        self.created_time = datetime.now()

    def to_json(self):
        return {
            'title': self.title,
            'content': self.content,
            'time': self.created_time
        }
