package com.medinote.medinotebackspring.openai;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "openai")
@Component
@NoArgsConstructor
public class OpenAIProperties {
    private String apiKey;
    private String gptModel;
    private String audioModel;
    private Urls urls = new Urls();

    @Getter
    @Setter
    @NoArgsConstructor
    public static final class Urls {
        private String baseUrl;
        private String chatUrl;
        private String transcriptionUrl;
    }

    public String fullGptUrl() {
        return urls.getBaseUrl() + urls.getChatUrl();
    }

    public String fullWhisperUrl() {
        return urls.getBaseUrl() + urls.getTranscriptionUrl();
    }

    public String textToNoteSysPrompt() {
        return """
            너의 목표는 한국어로 강의된 의과대학의 강의녹음본의 재가공이다.
            너에게 speech to text model이 변환한 한국 의과대학 강의녹음대본이 제공될 것이다.
            해당 대본은 문맥상 이상한 부분이나, 영문으로 된 의학전문용어가 잘못 변환된 경우가 많다. 너는 목표는 이러한 오류를 교정하는 것이다.
            대본에서 문장이 완결되지 않거나 오류가 있다면 수정하되, 문장에 없는 내용을 임의로 추가하면 안된다.
            줄임말의 경우 괄호를 추가해 full term을 적고, 일반적인 영문 의학용어인 경우 별도의 추가설명을 달지 마라.""";
    }

    public String textToNotePrompt(String rawText) {
        return """
            Record 1 : 로비 이라는 게 있습니다. 알비올라 로비올 있고 거기서 만들어진 제지 덕트를 따라서 쭉 올라와서 유두 아래쪽에 있는 티페어 사이너스로 해서 결국은 유도로 계구를 하게 되는데 이 하나하나의 시드는 제가 보통 표현할 때 포도 성이 이런 식으로 보통 환자들한테 많이 얘기를 하는데
                            
            Subgoal : Mark awkward sentences or words in square brackets in Record 1
            Answer : [로비] 이라는 게 있습니다. [알비올라 로비올] 있고 거기서 만들어진 [제지 덕트]를 따라서 쭉 올라와서 유두 아래쪽에 있는 [티페어 사이너스]로 해서 결국은 [유도]로 [계구]를 하게 되는데 이 하나하나의 [시드]는 제가 보통 표현할 때 [포도 성이] 이런 식으로 보통 환자들한테 많이 얘기를 하는데
                            
            Final Goal : Accurately correct Record1
            Correction1 : lobule 이라는 게 있습니다. Alveoli, lobule이 있고 거기서 만들어진 젖이 duct를 따라서 쭉 올라와서 유두 아래쪽에 있는 lactiferous sinus로 해서 결국은 유두로 개구를 하게 되는데 이 하나하나의 Segment가 제가 보통 표현할 때 포도송이 이런 식으로 보통 환자들한테 많이 얘기를 하는데
                            
            ---
                            
            Record2 : %s
            subgoal : Mark awkward sentences or words in square brackets in Record 1
                            
            Do not include an explanation in parentheses after the modified word.
            Consider the subgoals sequentially, as shown in the above example
            Do not arbitrarily omit or summarize lecture content.
            return most be string after "Correction2 :" and exclude "Correction2 :"
            
            Correction2 :""".formatted(rawText);
//        return """
//                ---
//                Record 1 : 로비 이라는 게 있습니다. 알비올라 로비올 있고 거기서 만들어진 제지 덕트를 따라서 쭉 올라와서 유두 아래쪽에 있는 티페어 사이너스로 해서 결국은 유도로 계구를 하게 되는데 이 하나하나의 시드는 제가 보통 표현할 때 포도 성이 이런 식으로 보통 환자들한테 많이 얘기를 하는데
//                Subgoal : Mark awkward sentences or words in square brackets in Record 1
//                Answer : [로비] 이라는 게 있습니다. [알비올라 로비올] 있고 거기서 만들어진 [제지 덕트]를 따라서 쭉 올라와서 유두 아래쪽에 있는 [티페어 사이너스]로 해서 결국은 [유도]로 [계구]를 하게 되는데 이 하나하나의 [시드]는 제가 보통 표현할 때 [포도 성이] 이런 식으로 보통 환자들한테 많이 얘기를 하는데
//                Final Goal : Accurately correct Record1
//                Correction1 : lobule 이라는 게 있습니다. Alveoli, lobule이 있고 거기서 만들어진 젖이 duct를 따라서 쭉 올라와서 유두 아래쪽에 있는 lactiferous sinus로 해서 결국은 유두로 개구를 하게 되는데 이 하나하나의 Segment가 제가 보통 표현할 때 포도송이 이런 식으로 보통 환자들한테 많이 얘기를 하는데
//                ---
//                Record2 : %s
//                ...
//
//                Do not include an explanation in parentheses after the modified word.
//                Consider the subgoals sequentially, as shown in the above example
//                Do not arbitrarily omit or summarize lecture\s
//                response only Record2's Correction2 text (exclude "Correction2 :") :
//
//                Correction2 :
//                """.formatted(rawText);
    }

    public String audioToTextPrompt() {
        return "Role : Your role is to make secondary edits to medical school lecture notes delivered in Korean. The audio to text program made a mistake, such as transcribing English medical terms exactly as they were pronounced in Korean. You should replace these mistakes with correct terminology, taking into account the context of the lecture. \\nMain Goal : Accurately correct records";
    }
}
