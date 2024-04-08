document.getElementById('send-btn').addEventListener('click', function() {
    var input = document.getElementById('chat-input');
    var message = input.value;
    if (message.trim() !== '') {
        var chatBox = document.getElementById('chat-box');
        chatBox.innerHTML += '<div>' + message + '</div>';


        const data = {
            model: "gpt-3.5-turbo",
            messages: [
                {
                    role: "system",
                    // gpt 역할이라든지 초기 설정 적어주기
                    content: "너는 어린 아이용 동화 작가야. 너는 사용자가 동화를 시작하고 싶을 때 이야기를 한 문장만 만들어 주면 돼. 그럼 사용자가 이어서 이야기의 다음 한 문장을 만들거야. 이런식으로 차근차근 이야기를 만들어 가면 돼"
                },
                {
                    role: "user",
                    content: message
                }
            ],
        };

        fetch('https://api.openai.com/v1/chat/completions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // !!!!!!! 밑에 API 추가해 줘야 함 !!!!!!!!
                'Authorization': 'Bearer ' // Bearer 뒤에 API 키 입력 ex)'Bearer sk-XXXX'
            },
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => chatBox.innerHTML += '<div>' + data.choices[0].message.content + '</div>')
        .catch((error) => {
            console.error('Error:', error);
        });

        input.value = '';
        chatBox.scrollTop = chatBox.scrollHeight;
    }
});

// 엔터 키로 메시지 전송 가능하게 만들기
document.getElementById('chat-input').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        document.getElementById('send-btn').click();
    }
});