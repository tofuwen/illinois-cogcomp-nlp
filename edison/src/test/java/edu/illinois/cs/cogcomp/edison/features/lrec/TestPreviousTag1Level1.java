/**
 * This software is released under the University of Illinois/Research and Academic Use License. See
 * the LICENSE file in the root folder for details. Copyright (c) 2016
 *
 * Developed by: The Cognitive Computation Group University of Illinois at Urbana-Champaign
 * http://cogcomp.cs.illinois.edu/
 */
package edu.illinois.cs.cogcomp.edison.features.lrec;

import edu.illinois.cs.cogcomp.core.utilities.DummyTextAnnotationGenerator;
import edu.illinois.cs.cogcomp.lbjava.parse.LinkedVector;
import edu.illinois.cs.cogcomp.ner.LbjFeatures.NETaggerLevel1;
import edu.illinois.cs.cogcomp.ner.LbjFeatures.NETaggerLevel2;
import edu.illinois.cs.cogcomp.ner.LbjFeatures.PreviousTag1Level1;
import edu.illinois.cs.cogcomp.ner.LbjTagger.*;
import edu.illinois.cs.cogcomp.ner.ParsingProcessingData.PlainTextReader;
import edu.illinois.cs.cogcomp.ner.config.NerBaseConfigurator;
import org.apache.commons.lang.ArrayUtils;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Constituent;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.PredicateArgumentView;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Relation;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.View;
import edu.illinois.cs.cogcomp.core.io.IOUtils;
import edu.illinois.cs.cogcomp.edison.features.FeatureCollection;
import edu.illinois.cs.cogcomp.edison.features.FeatureExtractor;
import edu.illinois.cs.cogcomp.edison.features.FeatureUtilities;
import edu.illinois.cs.cogcomp.edison.features.Feature;
import edu.illinois.cs.cogcomp.edison.utilities.CreateTestFeaturesResource;
import edu.illinois.cs.cogcomp.edison.utilities.CreateTestTAResource;
import edu.illinois.cs.cogcomp.edison.utilities.EdisonException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Random;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 *
 * @author Yewen Fan
 */
public class TestPreviousTag1Level1 extends TestCase {
    static Logger log = Logger.getLogger(TestAffixes.class.getName());

    private static List<TextAnnotation> tas;

    static {
        try {
            tas = IOUtils.readObjectAsResource(TestAffixes.class, "test.ta");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    private String labelForCurrentConstituent(View NER, Constituent current) {
        String value = "";
        List<String> labels = NER.getLabelsCovering(current);
        if (labels.size() > 0) {
            value = labels.get(0);
        }
        return value;
    }

    public final void test() throws EdisonException {

        log.debug("TestPreviousTag1Level1 Feature Extractor");

        TextAnnotation ta = tas.get(9);
        View NER = ta.getView(ViewNames.NER_CONLL);
        View TOKENS = ta.getView("TOKENS");

        log.debug("Got tokens FROM TextAnnotation");

        List<Constituent> testlist = TOKENS.getConstituentsCoveringSpan(0, 50);

        for (Constituent c : testlist) {
            log.debug(c.getSurfaceForm());
        }

        log.debug("Test Input size is " + testlist.size());

        PreviousTag1Level1Edison afx = new PreviousTag1Level1Edison("PreviousTag1Level1");

        log.debug("Printing Set of Features");

        for (int i = 0; i < testlist.size(); ++i) {
            String correct;
            if (i == 0) {
                correct = "PreviousTag1Level1:-1()"; // For the first constituent, it should not have previous label.
            } else {
                correct = labelForCurrentConstituent(NER, testlist.get(i-1)); // previous label
                correct = "PreviousTag1Level1:-1(" + correct + ")";
            }
            Constituent test = testlist.get(i);
            Set<Feature> feats = afx.getFeatures(test);
            for (Feature f : feats) {
                log.debug(f.getName());
                assertTrue(f.getName().equals(correct));
            }
        }
    }
}
