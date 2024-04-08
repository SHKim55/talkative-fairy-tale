from django.shortcuts import render
from django.http import HttpResponse

from openai import OpenAI
from .models import Story

# static variables
# gpt_client = OpenAI()


def story(request):
    return HttpResponse("여기는 새 스토리 만들기 / 기존 스토리 불러오기 를 선택하는 페이지가 들어가야합니다.")


def write_story(request):
    story = Story("No Title", "Test")
    print(story)
    context = {

    }
    return render(request, 'write.html', context)


def load_story():
    return


# def call_gpt(input_data):
#     response = gpt_client.chat.completions.create(
#         model="gpt-3.5-turbo",
#         messages=[
#             {
#                 "role": "system",
#                 "content": ""   # system prompt 작성
#             },
#             {
#                 "role": "user",
#                 "content": input_data   # user가 입력한 데이터
#             }
#         ]
#     )
#
#     output_data = response.choices[0].message
#     print(output_data)
#     return output_data

