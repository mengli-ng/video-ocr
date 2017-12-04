package xyz.dreamcoder.client;

import com.netflix.hystrix.HystrixCommand;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Meng Li
 */
@FeignClient(name = "baiduBCEClient", url = "https://aip.baidubce.com", configuration = FormUrlEncodedConfiguration.class)
public interface BaiduBCEClient {

    @PostMapping("/oauth/2.0/token?grant_type=client_credentials")
    BaiduAccessToken getAccessToken(@RequestParam("client_id") String clientId,
                                    @RequestParam("client_secret") String clientSecret);

    @PostMapping(value = "${baidu.ocr.url}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    HystrixCommand<OCRResult> accurateBasicOCR(Map<String, ?> parameters);
}

class FormUrlEncodedConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}