package com.softgallery.talkativefairytale.moderation;

import com.softgallery.talkativefairytale.dto.WordFilterDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordFilterTest {

    @Test
    public void testHasBadWord_withViolentOrCriminalWord() {
        String input = "이 텍스트에는 폭력이 포함되어 있습니다.";
        WordFilterDTO result = WordFilter.hasBadWord(input);
        assertTrue(result.isBad());
        assertEquals("Contains violent or criminal words: 폭력", result.getReason());
    }

    @Test
    public void testHasBadWord_withSexuallyOffensiveWord() {
        String input = "이 텍스트에는 성교라는 단어가 포함되어 있습니다.";
        WordFilterDTO result = WordFilter.hasBadWord(input);
        assertTrue(result.isBad());
        assertEquals("Contains sexually offensive words: 성교", result.getReason());
    }

    @Test
    public void testHasBadWord_withSelfHarmWord() {
        String input = "이 텍스트에는 자살이라는 단어가 포함되어 있습니다.";
        WordFilterDTO result = WordFilter.hasBadWord(input);
        assertTrue(result.isBad());
        assertEquals("Contains violent or criminal words: 자살", result.getReason());
    }

    @Test
    public void testHasBadWord_withHatefulWord() {
        String input = "이 텍스트에는 인종차별이라는 단어가 포함되어 있습니다.";
        WordFilterDTO result = WordFilter.hasBadWord(input);
        assertTrue(result.isBad());
        assertEquals("Contains hateful words: 인종차별", result.getReason());
    }

    @Test
    public void testHasBadWord_withSexualMinorWord() {
        String input = "이 텍스트에는 범죄라는 단어가 포함되어 있습니다.";
        WordFilterDTO result = WordFilter.hasBadWord(input);
        assertTrue(result.isBad());
        assertEquals("Contains violent or criminal words: 범죄", result.getReason());
    }

    @Test
    public void testHasBadWord_withGraphicViolenceWord() {
        String input = "이 텍스트에는 살해라는 단어가 포함되어 있습니다.";
        WordFilterDTO result = WordFilter.hasBadWord(input);
        assertTrue(result.isBad());
        assertEquals("Contains graphic violence words: 살해", result.getReason());
    }

    @Test
    public void testHasBadWord_withKoreanProfanityWord() {
        String input = "이 텍스트에는 씨발이라는 단어가 포함되어 있습니다.";
        WordFilterDTO result = WordFilter.hasBadWord(input);
        assertTrue(result.isBad());
        assertEquals("Contains Korean profanity words: 씨발", result.getReason());
    }

    @Test
    public void testHasBadWord_withDifficultWordForChildren() {
        String input = "이 텍스트에는 철학이라는 단어가 포함되어 있습니다.";
        WordFilterDTO result = WordFilter.hasBadWord(input);
        assertTrue(result.isBad());
        assertEquals("Contains difficult words for children: 철학", result.getReason());
    }

    @Test
    public void testHasBadWord_withNoProhibitedWords() {
        String input = "이 텍스트에는 금지된 단어가 포함되어 있지 않습니다.";
        WordFilterDTO result = WordFilter.hasBadWord(input);
        assertFalse(result.isBad());
        assertEquals("No prohibited words found", result.getReason());
    }
}
