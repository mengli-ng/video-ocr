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
@FeignClient(name = "baiduBCEClient", url = "${baidu.ocr.base-url}")
public interface BaiduBCEClient {

    @PostMapping(value = "${baidu.ocr.url}")
    HystrixCommand<OCRResult> accurateBasicOCR(Map<String, ?> parameters);
}