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

public class CurvedRankingTest {

    @Tested
    CurvedRanking curvedRanking;


    @Test
    public void testRankingWorks(@Injectable List<ValidatorEntry> list, @Injectable Iterator iterator) throws Exception {
        ValidatorEntry validatorEntry = new ValidatorEntry(null, 1, new ArrayList<Violation>());
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
        assertThat(ranking).isEqualTo(0.1f);
    }

    @Test
    public void testCurve() throws Exception {
        int maxRange = 100;
        float sum = 0f;
        for (int i = 1; i < maxRange; i++) {
            sum += curvedRanking.curvedQuotient(i, maxRange);
        }
    }
}
