package memberSearch.memberSearch.converter;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.IpPort;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort, String> {
    @Override
    public String convert(IpPort source) {
        String ipAddress = source.getIp() + ":" + source.getPort();
        log.info("from={}, to={}", source, ipAddress);
        return ipAddress;
    }
}
