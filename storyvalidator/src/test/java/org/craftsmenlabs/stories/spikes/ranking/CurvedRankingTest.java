package org.craftsmenlabs.stories.spikes.ranking;


import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.scoring.Violation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CurvedRankingTest {

    @Tested
    CurvedRanking curvedRanking;


    @Test
    public void testRankingWorks(@Injectable List<ValidatorEntry> list, @Injectable Iterator iterator) throws Exception {
        ValidatorEntry validatorEntry = new ValidatorEntry("", 1, 100, null, new ArrayList<Violation>());
        List<ValidatorEntry> testEntries = Arrays.asList(validatorEntry);

        new Expectations() {
            {
                list.size();
                result = 4;

                list.iterator();
                result = testEntries.iterator();
            }
        };
        float ranking = curvedRanking.createRanking(list);
        assertThat(ranking).isEqualTo(10.0f);
    }

    @Test
    public void testCurve() throws Exception {
        int maxRange = 100;
        float sum = 0f;
        for (int i = 1; i < maxRange; i++) {
            sum += curvedRanking.curvedQuotient(i, maxRange);
        }
        assertThat(sum).isEqualTo(1.0f);
    }
}
