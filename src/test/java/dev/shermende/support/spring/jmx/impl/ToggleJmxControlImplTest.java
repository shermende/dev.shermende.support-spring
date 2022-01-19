package dev.shermende.support.spring.jmx.impl;

import dev.shermende.support.spring.jmx.ToggleJmxControl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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