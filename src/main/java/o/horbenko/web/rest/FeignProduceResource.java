package o.horbenko.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sun.xml.internal.bind.v2.model.runtime.RuntimeReferencePropertyInfo;
import o.horbenko.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produce")
public class FeignProduceResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PutMapping("/produce-without-exception")
    @Timed
    public FooDTO produceSomeEntityWitoutException() {
        log.debug("FeignProduceResource.produceSomeEntityWitoutException");
        return new FooDTO();
    }

    @PutMapping("/produce-exception")
    @Timed
    public FooDTO produceException() {
        log.debug("FeignProduceResource.produceException");
        throw new RuntimeException("Feign-producer RuntimeException");
    }

    @PutMapping("/produce-custom-exception")
    @Timed
    public FooDTO produceCustomException() {
        log.debug("FeignProduceResource.produceException");
        throw new CustomRuntimeException();
    }

    @PutMapping("/produce-another-exception")
    public FooDTO produceNotAuthorizedException() {
        throw new AnotherCustomRuntimeException();
    }

    private static class AnotherCustomRuntimeException extends RuntimeException {
        private static final String DEFAULT_MESSAGE = " YO! THIS EXCEPTION NOT REGISTERED";

        public AnotherCustomRuntimeException() {
            super(DEFAULT_MESSAGE);
        }
    }

    private static class FooDTO {

        private String someString;
        private FooDTO someInnerEntity;

        public FooDTO() {
            this.someString = "DEFAULT PARENT";
            this.someInnerEntity = new FooDTO("DEFAULD CHILD");
        }

        public FooDTO(String someString) {
            this.someString = someString;
        }

        @Override
        public String toString() {
            return "FooDTO{" +
                "someString='" + someString + '\'' +
                ", someInnerEntity=" + someInnerEntity +
                '}';
        }

        public String getSomeString() {
            return someString;
        }

        public void setSomeString(String someString) {
            this.someString = someString;
        }

        public FooDTO getSomeInnerEntity() {
            return someInnerEntity;
        }

        public void setSomeInnerEntity(FooDTO someInnerEntity) {
            this.someInnerEntity = someInnerEntity;
        }
    }


}
