package com.dojo.guava.misc;

import static com.dojo.team.PersonBuilder.onePerson;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.RoundingMode;

import org.testng.annotations.Test;

import com.dojo.team.Person;
import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.html.HtmlEscapers;
import com.google.common.net.UrlEscapers;

public class GuavaShould {

    @Test
    public void createWatcher() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(500);
        stopwatch.stop();
        System.out.println("Do something took " + stopwatch);
    }
    
    @Test
    public void convertStringToEnumIfPresent() {
        Optional<RoundingMode> roundingMode = 
                    Enums.getIfPresent(RoundingMode.class, "HALF_UP");
    
        org.assertj.guava.api.Assertions.assertThat(roundingMode).isPresent();
        
        roundingMode = 
                Enums.getIfPresent(RoundingMode.class, "NOEXISTS");
        org.assertj.guava.api.Assertions.assertThat(roundingMode).isAbsent();
    }
    
    @Test
    public void checkNotNull() {
        Person lea = onePerson().withName("lea").build();
        Preconditions.checkNotNull(lea).getName();
    }
    
    @Test
    public void escapeHtml() {

        String html = HtmlEscapers.htmlEscaper().escape("1 < 2");
        assertThat(html).isEqualTo("1 &lt; 2");
        
        String url = UrlEscapers.urlFormParameterEscaper().escape("x\ny");
        assertThat(url).isEqualTo("x%0Ay");
    }

}
