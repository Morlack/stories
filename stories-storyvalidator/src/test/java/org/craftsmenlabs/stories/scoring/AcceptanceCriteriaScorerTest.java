package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.craftsmenlabs.stories.api.models.violation.CriteriaViolation;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class AcceptanceCriteriaScorerTest {

    private String goodCriteria =
            "Given I have 100 shares of MSFT stock\n" +
                    "And I have 150 shares of APPL stock\n" +
                    "And the time is before close of trading\n" +
                    "When I ask to sell 20 shares of MSFT stock\n" +
                    "Then I should have 80 shares of MSFT stock";


    @Test
    public void testPerformScorer_ReturnsZeroOnEmpty(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        List<Violation> v = new ArrayList<>();
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = "";

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;

        }};

        float score = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsNullOnEmpty(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = null;


            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;
        }};

        float score = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerAddsGivenClauseViolationOnNoGiven(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("gooooven ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.9999f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f - 0.33333f, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new CriteriaViolation(ViolationType.CriteriaGivenClauseViolation, ""));
    }

    @Test
    public void testPerformScorerAddsWhenClauseViolationOnNoGiven(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("whooon ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.9999f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f - 0.33333f, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new CriteriaViolation(ViolationType.CriteriaWhenClauseViolation, ""));
    }

    @Test
    public void testPerformScorerAddsThenClauseViolationOnNoGiven(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("thooon ");


            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.9999f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f - 0.33333f, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new CriteriaViolation(ViolationType.CriteriaThenClauseViolation, ""));
    }

    @Test
    public void testPerformScorerAndRatesFail(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig)
    {
        new Expectations()
        {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");

            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.3334f;
        }};

        AcceptanceCriteriaValidatorEntry ae = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(ae.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerAndRatesSuccess(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig)
    {
        new Expectations()
        {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");

            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.3333f;

        }};

        AcceptanceCriteriaValidatorEntry ae = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(ae.getRating()).isEqualTo(Rating.SUCCES);
    }

    @Test
    public void testPerformScorerMatchesAllKeywords(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.9999f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1f, withinPercentage(1));
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCES);
    }


    @Test
    public void testPerformScorerDoesntMatchGivenKeyword(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("goooven ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.6f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.6666f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchWhenKeyword(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("whoooooon ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.6666f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchThenKeyword(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("thoooon ");


            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.6666f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScoreCriteriaTooShort(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = "given when then given when then given when then ".substring(0, AcceptanceCriteriaScorer.MINIMUM_LENGTH_OF_ACC_CRITERIA - 1);

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.0f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerCriteriaRightLength(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");

            entry.getIssue().getAcceptanceCriteria();
            result = "given when then given when then given when then ".substring(0, AcceptanceCriteriaScorer.MINIMUM_LENGTH_OF_ACC_CRITERIA);

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerReturnsFailOnLowScore(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getIssue().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingtreshold();
            result = 1.1f;
        }};

        Rating rating = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getRating();
        assertThat(rating).isEqualTo(Rating.FAIL);
    }

}
