package com.common.allutils.pinyin_util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Author: 陈仁鑫
 * @Date: Create in  2020/10/15
 */
@Slf4j
public class ChinesePinyinUtil {

    /**
     * 将文字转为汉语拼音
     * @param chineseLanguage 要转成拼音的中文
     * @param type 大小写
     */
    private static String toChinesePinyin(String chineseLanguage, HanyuPinyinCaseType type){
        char[] clChars = chineseLanguage.trim().toCharArray();
        StringBuilder chinesePinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(type);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (char clChar : clChars) {
                String str = String.valueOf(clChar);
                // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                if (str.matches("[\u4e00-\u9fa5]+")) {
                    chinesePinyin.append(PinyinHelper.toHanyuPinyinStringArray(
                            clChar, defaultFormat)[0]);

                    // 如果字符是数字,取数字
                } else if (str.matches("[0-9]+")) {
                    chinesePinyin.append(clChar);

                    // 如果字符是字母,取字母
                } else if (str.matches("[a-zA-Z]+")) {

                    if (type.equals(HanyuPinyinCaseType.UPPERCASE)) {
                        chinesePinyin.append(str.toUpperCase());
                    }else {
                        chinesePinyin.append(str.toLowerCase());
                    }

                }

            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("字符, {} 无法进行转换, {}", chineseLanguage, e.getMessage());
        }
        return chinesePinyin.toString();
    }


    /**
     * 取第一个字符 (大写)
     * @param chineseLanguage 中文
     * @return 首字符大写
     */
    public static String getFirstLetterUp(String chineseLanguage){
        return getFirstLetter(chineseLanguage, HanyuPinyinCaseType.UPPERCASE);

    }
    /**
     * 取第一个字符   (小写)
     * @param chineseLanguage 中文
     * @return 首字符小写
     */
    public static String getFirstLetterLow(String chineseLanguage){
        return getFirstLetter(chineseLanguage, HanyuPinyinCaseType.LOWERCASE);
    }

    private static String getFirstLetter(String chineseLanguage, HanyuPinyinCaseType type){
        char[] chars = toChinesePinyin(chineseLanguage, type).toCharArray();
        return String.valueOf(chars[0]);
    }

}
