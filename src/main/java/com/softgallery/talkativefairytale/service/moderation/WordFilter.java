package com.softgallery.talkativefairytale.service.moderation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softgallery.talkativefairytale.dto.WordFilterDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.softgallery.talkativefairytale.config.ChatGptConfig.API_KEY;

@Service
@PropertySource("classpath:openai.properties")
public class WordFilter {
    private static final String GPT_MODERATION_API_URL = "https://api.openai.com/v1/moderations";
    private static final String BAD_DATA_INDICATOR = "{[ReJeCTed!]}";
    private static final List<String> VIOLENT_OR_CRIMINAL_WORDS = Arrays.asList(
            "폭력", "범죄", "살인", "강도", "강간", "납치", "테러", "학대",
            "방화", "절도", "협박", "마약", "성폭력", "성범죄", "매춘",
            "자살", "살인자", "범법", "피투성이", "범죄 행위", "유괴",
            "살인 사건", "강도 사건", "성범죄 사건", "사기 사건", "폭력 사건", "폭행 사건", "방화 사건",
            "협박 사건", "마약 사건", "성폭행", "성희롱", "성추행", "성적 학대", "성적 착취", "범죄 조직", "폭동",
            "무단 침입", "죽이다", "죽이고", "죽이자",
            "무차별", "인신매매", "학살",
            "범죄 통계",
            "교도소", "조직폭력", "패륜", "반사회적",
            "교살","협박 전화", "밀매", "무기 거래",
            "범죄 실행", "유린", "몰살", "피살", "암거래", "불법 거래",
            "테러리스트", "전기톱"
    );

    private static final List<String> SEXUALLY_OFFENSIVE_WORDS = Arrays.asList(
            "성기", "음경", "성교", "성관계", "포르노", "야동", "나체", "음란물",
            "섹스", "성행위", "성적 취향", "성적 욕망", "성적 판타지", "에로틱", "음란",
            "성기능", "성적 흥분", "성인물", "성욕", "변태", "매춘", "성적 대상화", "성노예",
            "음부", "오르가즘", "애무", "노출증", "바람피다", "불륜", "외도", "정사 장면",
            "성병", "사정", "애널", "오럴", "구강 성교", "동성애", "동성 성행위", "변태 성욕", "성도착",
            "성적 학대", "성적 착취", "성적 괴롭힘", "성적 희롱", "성적 모욕", "성폭력", "성희롱",
            "성추행", "성적 비하", "성적 유린", "성적 학대"
    );

    private static final List<String> SELF_HARM_WORDS = Arrays.asList(
            "자해", "자살", "목매다", "면도날", "출혈",
            "자학", "스스로 해치다", "극단적 선택", "목매기",
            "목 졸리기", "극단적 행동", "정신적 고통", "자기 파괴",
            "삶의 의욕 상실", "극단적 상황", "삶의 포기", "생명 경시",
            "고통스러운 삶", "극단적 스트레스", "정신적 아픔", "정신적 상처",
            "내적 고통", "죽음의 유혹", "자신을 상처 입히다"
    );

    private static final List<String> HATEFUL_WORDS = Arrays.asList(
            "인종차별", "성차별", "혐오 발언",
            "혐오 표현", "증오 표현", "모욕적인", "모욕하다", "경멸하다", "혐오감",
            "인종차별주의자", "성차별주의자", "동성애 혐오", "외국인 혐오", "반유대주의", "반이슬람주의",
            "장애인 차별", "성적 지향 차별", "민족 차별", "국적 차별", "종교 차별", "계급 차별",
            "혐오 범죄", "증오 범죄", "편견에 찬", "증오심", "적대감", "적대적인",
            "혐오감 조장", "증오심 조장", "모욕적인 발언", "모욕적인 행동", "경멸적인 표현", "멸시하는",
            "혐오 대상", "인종적 편견", "성적 편견", "종교적 편견", "문화적 편견", "민족적 편견",
            "혐오 선동", "혐오 단체", "혐오 주의자", "증오 단체", "증오 선동", "성적 비하", "성적 모욕",
            "종교적 모욕", "민족적 비하", "민족적 모욕", "국적 비하", "국적 모욕", "동성애 혐오 발언",
            "트랜스젠더 혐오", "성소수자 혐오", "여성 혐오", "남성 혐오", "나이 차별", "세대 차별",
            "장애 혐오", "정신적 장애 비하", "지체 장애 비하", "정신병 비하", "지적 장애 비하"
    );

    private static final List<String> SEXUAL_MINOR_WORDS = Arrays.asList(
            "아동 성범죄", "미성년자 성범죄", "아동 포르노", "미성년자 포르노", "아동 성학대", "미성년자 성학대",
            "아동 성착취", "미성년자 성착취", "아동 성희롱", "미성년자 성희롱", "아동 성추행", "미성년자 성추행",
            "아동 성폭력", "미성년자 성폭력", "아동 음란물", "미성년자 음란물", "아동 나체", "미성년자 나체",
            "아동 유혹", "미성년자 유혹", "아동 성교", "미성년자 성교", "아동 성적 대상화", "미성년자 성적 대상화",
            "아동 성적 학대", "미성년자 성적 학대", "아동 성적 착취", "미성년자 성적 착취", "아동 음란", "미성년자 음란",
            "아동 성기", "미성년자 성기", "아동 성행위", "미성년자 성행위", "아동 성적 판타지", "미성년자 성적 판타지",
            "아동 에로틱", "미성년자 에로틱", "아동 성적 흥분", "미성년자 성적 흥분", "아동 성욕", "미성년자 성욕"
    );

    private static final List<String> GRAPHIC_VIOLENCE_WORDS = Arrays.asList(
            "피투성이", "살해", "도살", "잔인한", "참수", "절단", "참수", "고문",
            "학살", "폭력적인", "비참한", "혈흔", "신체 훼손", "시체", "사체", "살점",
            "내장", "잔혹성", "피투성이의", "피바다", "폭발물",
            "혈액", "고어", "끔찍한 장면", "핏자국", "살상", "폭발 사고",
            "폭행 사건", "흉악한", "파열", "피를 흘리다", "신체 절단",
            "찌르다", "난도질", "끓는 물", "살상 무기", "잔혹 행위", "참혹한 죽음", "폭력 사태",
            "폭력 범죄", "잔혹한 공격", "잔인한 폭력", "잔혹한 살인", "흉기",
            "피부 찢김", "신체 손상", "피칠갑"
    );

    private static final List<String> KOREAN_PROFANITY_WORDS = Arrays.asList(
            "씨발", "ㅅㅂ", "ㅂㅅ", "ㅄ", "병신", "미친놈", "미친년", "지랄", "닥쳐", "꺼져", "시발", "바보", "멍청이", "뚱댕이",
            "뚱땡이", "돼지", "씹년", "멍충이", "멍1청이", "^^ㅣ발", "야 이 씨X", "씹새끼야", "미친ㄴ", "또라이새끼", "ㄲㅈ", "ㅁㅊㄴ",
            "개새끼", "개년", "개자식", "죽여버리다", "죽여버려", "쌍놈", "쌍년", "후레자식", "후레년", "후레놈",
            "닥치다", "꺼지다", "돌아이", "돌대가리", "똘아이", "멍청이", "바보", "멍충이", "쓰레기",
            "인간말종", "시궁창", "찌질이", "등신", "재수없다", "꼴통", "싸가지", "닥치라고", "빡친",
            "빡치다", "빡쳐", "앰생", "개같은", "병신같은", "씹새끼", "씹년", "씹자식", "뇌텅텅", "멍텅구리",
            "한심한", "쪼다", "존나", "존나게", "존맛", "존싫", "존좋", "병맛", "뻐큐", "뻐킹",
            "어이없다", "허접", "쪽팔리다", "쪽팔린", "쪽팔려", "짜증나", "짜증나게", "짜증", "개짜증",
            "노답", "극혐", "관종", "꼰대", "꼴통", "개망나니", "싸가지없는", "돌대가리", "천치",
            "쓰레기같은", "엿같다", "엿먹어라", "개자식", "개저씨", "개잡놈", "개찌질이", "개쌍놈",
            "개쌍년", "개씹년", "개씹놈", "씨팔", "시팔", "시발", "시펄", "씨펄", "개지랄",
            "개씹", "병쉰", "병싄", "병1신", "병2신", "병3신", "개좆", "좆", "좆같다", "좆까", "엿먹어",
            "엿같다", "쌍놈", "쌍년", "후레자식", "후레년", "후레놈", "닥치다", "꺼지다", "돌아이",
            "돌대가리", "똘아이", "멍청이", "바보", "멍충이", "쓰레기", "인간말종", "시궁창", "찌질이",
            "등신", "재수없다", "꼴통", "싸가지", "닥치라고", "빡친", "빡치다", "빡쳐", "앰생",
            "좆같은", "개같은", "병신같은", "씹새끼", "씹년", "씹자식", "뇌텅텅", "멍텅구리", "한심한",
            "쪼다", "존나", "존나게", "존맛", "존싫", "존좋", "병맛", "뻐큐", "뻐킹", "어이없다",
            "허접", "쪽팔리다", "쪽팔린", "쪽팔려", "짜증나", "짜증나게", "짜증", "개짜증", "노답",
            "극혐", "관종", "꼰대", "꼴통", "좆밥", "개망나니", "싸가지없는", "돌대가리", "천치",
            "쓰레기같은", "좆같다", "엿같다", "좆까", "엿먹어라", "개자식", "개저씨", "개잡놈",
            "개찌질이", "개쌍놈", "개쌍년", "개씹년", "개씹놈", "씨팔", "시팔", "시발", "시펄", "시1발", "씨1발",
            "씨펄", "개지랄", "개씹", "개씹새", "병쉰", "병싄", "병1신", "병2신", "병3신", "개좆",
            "좆까라", "좆털", "좆빨다", "좆빠는", "좆빨기", "좆빠는", "좆빨아라", "좆빨아줘", "좆빨이",
            "좆터리", "좆짜다", "좆맛나", "좆맛나게", "좆대가리", "좆도모른다", "좆모르다", "좆모름",
            "좆도없다", "좆밥", "좆꼴리다", "좆치다", "좆치기", "좆쓰레기", "좆찌질이", "좆딸",
            "좆딸치다", "좆따다", "좆만해", "좆찢다", "좆끼", "좆귀신", "좆개", "좆탱", "좆탱이",
            "좆간지럽다", "좆간지럽게", "좆질", "좆질하네", "좆질하다", "좆빠네", "좆빨아", "좆빨아라",
            "좆빨아줘", "좆빠", "좆도모름", "좆간지럽다", "좆간지럽게", "좆질하네", "좆질하다", "좆빠네",
            "좆빨아줘", "좆빨이", "좆터리", "좆짜다", "좆맛나", "좆나", "좆대가리", "좆모르다",
            "좆모름", "좆도없다", "좆밥", "좆꼴리다", "좆치다", "좆치기", "좆쓰레기", "좆찌질이",
            "좆딸", "좆딸치다", "좆따다", "좆만해", "좆찢다", "좆끼", "좆귀신", "좆개", "좆탱",
            "좆탱이", "좆간지럽다", "좆간지럽게", "좆질", "좆질하네", "좆질하다", "좆빠네", "좆빨아",
            "좆빨아라", "좆빨아줘", "좆빨기", "좆빠는", "좆빨아", "좆빠", "좆빨이", "좆터리", "좆짜다",
            "좆맛나", "좆나", "좆대가리", "좆도", "좆도모른다", "좆모르다", "좆모름", "좆도없다", "좆밥",
            "좆꼴리다", "좆치다", "좆치기", "좆쓰레기", "좆찌질이", "좆딸", "좆딸치다", "좆따다", "좆만해",
            "좆까라", "좆찢다", "좆끼", "좆빠는", "좆찌질이", "좆귀신", "좆개", "좆같아", "좆같네", "좆치다",
            "좆탱", "좆탱이", "좆간지럽다", "좆간지럽게", "좆질", "좆질하네", "좆질하다", "좆빠네", "좆빠",
            "좆빨아", "좆빨아라", "좆빨아줘", "좆빨기", "좆빠는", "좆빨아", "좆빠", "좆빨이", "좆터리",
            "좆짜다", "좆맛나", "좆나", "좆대가리", "좆도", "좆도모른다", "좆모르다", "좆모름", "좆도없다",
            "좆밥", "좆꼴리다", "좆치다", "좆치기", "좆쓰레기", "좆찌질이", "좆딸", "좆딸치다", "좆따다",
            "좆만해", "좆까라", "좆찢다", "좆끼", "좆빠는", "좆찌질이", "좆귀신", "좆개", "좆같아", "좆같네",
            "좆치다", "좆탱", "좆탱이", "좆간지럽다", "좆간지럽게", "좆질", "좆질하네", "좆질하다", "좆빠네",
            "좆빠", "좆빨아", "좆빨아라", "좆빨아줘", "좆빨기", "좆빠는", "좆빨아", "좆빠", "좆빨이", "좆터리",
            "좆짜다", "좆맛나", "좆나", "좆대가리", "좆도", "좆도모른다", "좆모르다", "좆모름", "좆도없다",
            "좆밥", "좆꼴리다", "좆치다", "좆치기", "좆쓰레기", "좆찌질이", "좆딸", "좆딸치다"
    );

    private static final List<String> DIFFICULT_WORDS_FOR_CHILDREN = Arrays.asList(
            "철학", "정치", "경제", "사회", "문화", "예술", "역사", "심리학", "사회학", "인류학",
            "생물학", "물리학", "화학", "지구과학", "기후변화", "환경오염", "지구온난화", "탄소배출",
            "생태계", "유전자", "진화론", "양자역학", "상대성이론", "민주주의", "공산주의", "사회주의",
            "자본주의", "전체주의", "독재", "독재자", "선거", "투표", "정당", "의회", "입법", "행정",
            "사법", "헌법", "법률", "조약", "외교", "외교관", "전쟁", "평화", "안보", "국방", "군사",
            "외환", "통화", "금융", "증권", "주식", "채권", "투자", "재테크", "부동산", "자산", "부채",
            "상속", "증여", "기부", "봉사", "자원봉사", "비영리", "기부금", "세금", "세율", "소득세",
            "법인세", "부가가치세", "재정", "예산", "적자", "흑자", "균형재정", "재정정책", "통화정책",
            "금리", "물가", "인플레이션", "디플레이션", "경기", "경기부양", "경기침체", "불황", "호황",
            "공황", "경제성장", "경제발전", "국내총생산", "GDP", "GNP", "국민소득", "빈곤", "부유",
            "분배", "재분배", "소득격차", "빈부격차", "양극화", "노동", "노동자", "근로자", "고용",
            "실업", "취업", "실업률", "취업률", "노동시장", "노사관계", "파업", "직장", "기업", "산업",
            "제조업", "서비스업", "농업", "수산업", "광업", "에너지", "자원", "자원개발", "자원보존",
            "재생에너지", "태양광", "풍력", "수력", "원자력", "핵에너지", "핵발전", "원전", "핵폐기물",
            "방사능", "오염", "환경보호", "자연보존", "생물다양성", "멸종위기", "기후변화", "지구온난화",
            "온실가스", "탄소배출", "탄소중립", "지속가능성", "친환경", "환경정책", "국제정치", "세계정치",
            "글로벌", "다자간", "양자간", "국제관계", "외교정책", "외교관계", "국제법", "국제기구", "유엔",
            "UN", "세계은행", "IMF", "세계무역기구", "WTO", "세계보건기구", "WHO", "국제노동기구",
            "ILO", "국제통화기금", "IMF", "국제연합", "UN", "국제협력", "외교적", "정치적", "경제적",
            "사회적", "문화적", "역사적", "지리적", "기술적", "과학적", "교육적", "학문적", "철학적",
            "이념적", "종교적", "윤리적", "도덕적", "법적", "제도적", "제도", "규제", "규제정책", "자유화",
            "시장개방", "무역", "수출", "수입", "무역적자", "무역흑자", "무역수지", "경상수지", "자본수지",
            "국제수지", "외환보유고", "환율", "환율변동", "통화가치", "금본위제", "금본위제도", "외환시장",
            "외환거래", "국제금융", "국제투자", "국제무역", "국제개발", "국제협력", "국제원조", "원조",
            "구호", "구호활동", "구호물자", "인도주의", "인권", "인권보호", "인권침해", "인권운동",
            "인권기구", "국제인권", "여성인권", "아동인권", "노동인권", "장애인인권", "소수자인권",
            "난민", "난민보호", "난민문제"
    );

    public static String getBadDataIndicator() { return BAD_DATA_INDICATOR; };

    public static WordFilterDTO doFilterWithGptModeration(String input) {
        // 로컬 단어 필터링을 먼저 수행
        WordFilterDTO localFilterResult = hasBadWord(input);
        if (localFilterResult.isBad()) {
            return localFilterResult;
        }

        // GPT Moderation API 호출
        WordFilterDTO gptFilterResult = callGptModerationApi(input);
        if (gptFilterResult.isBad()) {
            return gptFilterResult;
        }

        return new WordFilterDTO(false, "No prohibited words found");
    }

    public static WordFilterDTO hasBadWord(String input) {
        for (String word : VIOLENT_OR_CRIMINAL_WORDS) {
            if (input.contains(word)) {
                return new WordFilterDTO(true, "Contains violent or criminal words: " + word);
            }
        }
        for (String word : SEXUALLY_OFFENSIVE_WORDS) {
            if (input.contains(word)) {
                return new WordFilterDTO(true, "Contains sexually offensive words: " + word);
            }
        }
        for (String word : SELF_HARM_WORDS) {
            if (input.contains(word)) {
                return new WordFilterDTO(true, "Contains self-harm words: " + word);
            }
        }
        for (String word : HATEFUL_WORDS) {
            if (input.contains(word)) {
                return new WordFilterDTO(true, "Contains hateful words: " + word);
            }
        }
        for (String word : SEXUAL_MINOR_WORDS) {
            if (input.contains(word)) {
                return new WordFilterDTO(true, "Contains sexual minor words: " + word);
            }
        }
        for (String word : GRAPHIC_VIOLENCE_WORDS) {
            if (input.contains(word)) {
                return new WordFilterDTO(true, "Contains graphic violence words: " + word);
            }
        }
        for (String word : KOREAN_PROFANITY_WORDS) {
            if (input.contains(word)) {
                return new WordFilterDTO(true, "Contains Korean profanity words: " + word);
            }
        }
        for (String word : DIFFICULT_WORDS_FOR_CHILDREN) {
            if (input.contains(word)) {
                return new WordFilterDTO(true, "Contains difficult words for children: " + word);
            }
        }
        return new WordFilterDTO(false, "No prohibited words found");
    }

    private static WordFilterDTO callGptModerationApi(String input) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("input", input);

        try {
            String jsonRequest = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    GPT_MODERATION_API_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            String responseBody = response.getBody();
            Map<String, Object> jsonResponse = objectMapper.readValue(responseBody, Map.class);
            Map<String, Boolean> result = (Map<String, Boolean>)((List<Object>)jsonResponse.get("results")).get(0);

            boolean flagged = result.get("flagged");
            //String reason = result.containsKey("reason") ? (String) jsonResponse.get("reason") : "No specific reason provided";
            String reason = "Gpt moderation";

            return new WordFilterDTO(flagged, reason);

        } catch (Exception e) {
            e.printStackTrace();
            return new WordFilterDTO(true, "Error calling GPT Moderation API: " + e.getMessage());
        }
    }

}

