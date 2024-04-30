from django.urls import path
from . import views


urlpatterns = [
    path('', views.story),               # 스토리 생성 or 불러오기 선택
    path('write/', views.write_story),   # 스토리 생성 페이지
]