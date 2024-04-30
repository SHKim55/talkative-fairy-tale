from django.shortcuts import render
from django.http import HttpResponse


# Create your views here.
def home(request):
    return HttpResponse("여기는 홈페이지를 넣어야 됩니다.")

