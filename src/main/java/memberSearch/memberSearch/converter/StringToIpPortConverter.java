package memberSearch.memberSearch.converter;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.IpPort;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {
    @Override
    public IpPort convert(String source) {
        String[] split = source.split(":");
        IpPort ipPort = new IpPort(split[0], split[1]);
        log.info("from={}, to={}", source, ipPort);
        return ipPort;
    }
}
