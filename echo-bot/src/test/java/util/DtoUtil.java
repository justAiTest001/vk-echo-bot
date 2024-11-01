package util;

import ru.justai.vkechobot.dto.VkApiError;

public class DtoUtil {

    public static VkApiError createVkApiError() {
        var vkApiError = new VkApiError();
        vkApiError.setCode(14);
        vkApiError.setMessage("Captcha needed");
        return vkApiError;
    }
}
