/**
 * This software is released under the University of Illinois/Research and Academic Use License. See
 * the LICENSE file in the root folder for details. Copyright (c) 2016
 *
 * Developed by: The Cognitive Computation Group University of Illinois at Urbana-Champaign
 * http://cogcomp.cs.illinois.edu/
 */
// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// real% FeaturesLevel1Only$$5(NEWord word) <- PreviousTag1Level1 &&
// additionalFeaturesRealConjunctive

package edu.illinois.cs.cogcomp.ner.LbjFeatures;

import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.ner.ExpressiveFeatures.BrownClusters;
import edu.illinois.cs.cogcomp.ner.ExpressiveFeatures.Gazetteers;
import edu.illinois.cs.cogcomp.ner.ExpressiveFeatures.WordEmbeddings;
import edu.illinois.cs.cogcomp.ner.ExpressiveFeatures.WordTopicAndLayoutFeatures;
import edu.illinois.cs.cogcomp.ner.LbjTagger.NEWord;
import edu.illinois.cs.cogcomp.ner.LbjTagger.ParametersForLbjCode;
import edu.illinois.cs.cogcomp.ner.StringStatisticsUtils.*;
import java.util.*;


public class FeaturesLevel1Only$$5 extends Classifier {
    private static final PreviousTag1Level1 left = new PreviousTag1Level1();
    private static final additionalFeaturesRealConjunctive right =
            new additionalFeaturesRealConjunctive();

    public FeaturesLevel1Only$$5() {
        containingPackage = "edu.illinois.cs.cogcomp.ner.LbjFeatures";
        name = "FeaturesLevel1Only$$5";
    }

    public String getInputType() {
        return "edu.illinois.cs.cogcomp.ner.LbjTagger.NEWord";
    }

    public String getOutputType() {
        return "real%";
    }

    public FeatureVector classify(Object __example) {
        if (!(__example instanceof NEWord)) {
            String type = __example == null ? "null" : __example.getClass().getName();
            System.err
                    .println("Classifier 'FeaturesLevel1Only$$5(NEWord)' defined on line 444 of LbjTagger.lbj received '"
                            + type + "' as input.");
            new Exception().printStackTrace();
            System.exit(1);
        }

        FeatureVector __result;
        __result = new FeatureVector();
        FeatureVector leftVector = left.classify(__example);
        int N = leftVector.featuresSize();
        FeatureVector rightVector = right.classify(__example);
        int M = rightVector.featuresSize();
        for (int i = 0; i < N; ++i) {
            Feature lf = leftVector.getFeature(i);
            for (int j = 0; j < M; ++j) {
                Feature rf = rightVector.getFeature(j);
                __result.addFeature(lf.conjunction(rf, this));
            }
        }

        __result.sort();
        return __result;
    }

    public FeatureVector[] classify(Object[] examples) {
        if (!(examples instanceof NEWord[])) {
            String type = examples == null ? "null" : examples.getClass().getName();
            System.err
                    .println("Classifier 'FeaturesLevel1Only$$5(NEWord)' defined on line 444 of LbjTagger.lbj received '"
                            + type + "' as input.");
            new Exception().printStackTrace();
            System.exit(1);
        }

        return super.classify(examples);
    }

    public int hashCode() {
        return "FeaturesLevel1Only$$5".hashCode();
    }

    public boolean equals(Object o) {
        return o instanceof FeaturesLevel1Only$$5;
    }
}
