package cheongchul.cheongchul_eolam.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UniversityImageService {
    private final Map<String,String> universityImages;
    public UniversityImageService () {
        universityImages = new HashMap<>();
        universityImages.put("가천대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%84%8E%E1%85%A5%E1%86%AB%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.jpg");
        universityImages.put("건국대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A5%E1%86%AB%E1%84%80%E1%85%AE%E1%86%A8%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.jpg");
        universityImages.put("고려대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%85%E1%85%A7%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.jpg");
        universityImages.put("경북대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A7%E1%86%BC%E1%84%87%E1%85%AE%E1%86%A8%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("광운대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%AA%E1%86%BC%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("국민대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%AE%E1%86%A8%E1%84%86%E1%85%B5%E1%86%AB%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("명지대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%A7%E1%86%BC%E1%84%8C%E1%85%B5%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("부산대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%AE%E1%84%89%E1%85%A1%E1%86%AB%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("세종대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A6%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("성균관대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A5%E1%86%BC%E1%84%80%E1%85%B2%E1%86%AB%E1%84%80%E1%85%AA%E1%86%AB%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("한양대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A3%E1%86%BC%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.jpg");
        universityImages.put("연세대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A7%E1%86%AB%E1%84%89%E1%85%A6%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("서울대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A5%E1%84%8B%E1%85%AE%E1%86%AF%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("아주대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A1%E1%84%8C%E1%85%AE%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.jpg");
        universityImages.put("인천대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%8E%E1%85%A5%E1%86%AB%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("전남대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8C%E1%85%A5%E1%86%AB%E1%84%82%E1%85%A1%E1%86%B7%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("중앙대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8C%E1%85%AE%E1%86%BC%E1%84%8B%E1%85%A1%E1%86%BC%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.png");
        universityImages.put("홍익대학교", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%A8%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.jpg");
        universityImages.put("포항공대", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%91%E1%85%A9%E1%84%92%E1%85%A1%E1%86%BC%E1%84%80%E1%85%A9%E1%86%BC%E1%84%80%E1%85%AA%E1%84%83%E1%85%A2%E1%84%92%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AD.jpg");
        universityImages.put("defaultImg", "https://teamss-bucket.s3.ap-northeast-2.amazonaws.com/defaultUnilogo.png");
    }

    public String getUniversityImageUrl(String university) {
        return universityImages.getOrDefault(university, "defaultImg");
    }
}
