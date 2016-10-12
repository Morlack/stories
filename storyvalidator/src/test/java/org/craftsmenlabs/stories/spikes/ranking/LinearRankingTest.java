package org.craftsmenlabs.stories.spikes.ranking;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.api.models.Violation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class LinearRankingTest {

    @Tested
    LinearRanking linearRanking;

    @Test
    public void testRankingWorks(@Injectable List<ValidatorEntry> list, @Injectable Iterator iterator) throws Exception {
        ValidatorEntry validatorEntry = new ValidatorEntry(null, 0, new ArrayList<Violation>());
        List<ValidatorEntry> testEntries = Arrays.asList(validatorEntry);

        new Expectations() {
            {
                list.size();
                result = 4;

                list.iterator();
                result = testEntries.iterator();
            }
        };
        float ranking = linearRanking.createRanking(list);
        assertThat(ranking).isEqualTo(10.0f);
    }
}
