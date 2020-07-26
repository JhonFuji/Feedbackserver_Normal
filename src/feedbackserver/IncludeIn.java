/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedbackserver;

import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.TruthValue;

/**
 *
 * @author nobin
 */
public class IncludeIn {
    public static TruthValue thisFDotherFD(double _thisFuzz,double _thisDis, double _otherFuzz,double _otherDis){
        double distance = Math.abs( _otherDis-_thisDis);
        double fuzzinessSum = _thisFuzz+_otherFuzz;
        double nec;
        double pos;
        if (Double.isInfinite(fuzzinessSum)) {
            nec = 0;
            pos = 1;
        } else {
            nec = Math.max((_otherFuzz - distance) / fuzzinessSum, 0);
            pos = Math.max((fuzzinessSum - distance) / fuzzinessSum, 0);
        }
        if (Double.isNaN(nec) && Double.isNaN(pos)) {
            nec = 0.5;
            pos = 1;
        }
        return TruthValue.create(nec, pos);
    }
    public static TruthValue thisPotherDF(Point _this, double _distance, double _otherFuzziness){
        double distance = _distance;
        double fuzzinessSum = _this.fuzziness()+_otherFuzziness;
        double nec;
        double pos;
        if (Double.isInfinite(fuzzinessSum)) {
            nec = 0;
            pos = 1;
        } else {
            nec = Math.max((_otherFuzziness - distance) / fuzzinessSum, 0);
            pos = Math.max((fuzzinessSum - distance) / fuzzinessSum, 0);
        }
        if (Double.isNaN(nec) && Double.isNaN(pos)) {
            nec = 0.5;
            pos = 1;
        }
        return TruthValue.create(nec, pos);
    }
    public static TruthValue thisPotherP(Point _this, Point _other){
        double distance = _this.distance(_other);
        double fuzzinessSum = _this.fuzziness()+_other.fuzziness();
        double nec;
        double pos;
        if (Double.isInfinite(fuzzinessSum)) {
            nec = 0;
            pos = 1;
        } else {
            nec = Math.max((_other.fuzziness() - distance) / fuzzinessSum, 0);
            pos = Math.max((fuzzinessSum - distance) / fuzzinessSum, 0);
        }
        if (Double.isNaN(nec) && Double.isNaN(pos)) {
            nec = 0.5;
            pos = 1;
        }
        return TruthValue.create(nec, pos);
    }
    public static TruthValue thisPotherPF(Point _this, Point _other, double _otherFuzzyness){
        double distance = _this.distance(_other);
        double fuzzinessSum = _this.fuzziness()+_otherFuzzyness;
        double nec;
        double pos;
        if (Double.isInfinite(fuzzinessSum)) {
            nec = 0;
            pos = 1;
        } else {
            nec = Math.max((_otherFuzzyness - distance) / fuzzinessSum, 0);
            pos = Math.max((fuzzinessSum - distance) / fuzzinessSum, 0);
        }
        if (Double.isNaN(nec) && Double.isNaN(pos)) {
            nec = 0.5;
            pos = 1;
        }
        return TruthValue.create(nec, pos);
    }
}
