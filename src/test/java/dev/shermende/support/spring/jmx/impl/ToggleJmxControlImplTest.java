package dev.shermende.support.spring.jmx.impl;

import dev.shermende.support.spring.jmx.ToggleJmxControl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class ToggleJmxControlImplTest {

    @Test
    public void isEnabled() {
        final ToggleJmxControl jmxControl = new ToggleJmxControlImpl(false);
        Assert.assertFalse(jmxControl.isEnabled());
    }

    @Test
    public void toggle() {
        final ToggleJmxControl jmxControl = new ToggleJmxControlImpl(false);
        Assert.assertFalse(jmxControl.isEnabled());
        Assert.assertTrue(jmxControl.toggle());
        Assert.assertTrue(jmxControl.isEnabled());
    }

}