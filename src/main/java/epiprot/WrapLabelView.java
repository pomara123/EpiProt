package epiprot;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.GlyphView;
import javax.swing.text.LabelView;

public class WrapLabelView extends LabelView {
    public WrapLabelView(Element elem) {
        super(elem);
    }

    public int getBreakWeight(int axis, float pos, float len) {
        if (axis == javax.swing.text.View.X_AXIS) {
            checkPainter();
            int p0 = getStartOffset();
            int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len);
            if (p1 == p0) {
                // can't even fit a single character
                return javax.swing.text.View.BadBreakWeight;
            }
            try {
                //if the view contains line break char return forced break
                if (getDocument().getText(p0, p1 - p0).indexOf("\r") >= 0) {
                    return javax.swing.text.View.ForcedBreakWeight;
                }
            }
            catch (BadLocationException ex) {
                //should never happen
            }
        }
        return super.getBreakWeight(axis, pos, len);
    }

    public javax.swing.text.View breakView(int axis, int p0, float pos, float len) {
        if (axis == javax.swing.text.View.X_AXIS) {
            checkPainter();
            int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len);
            try {
                //if the view contains line break char break the view
                int index = getDocument().getText(p0, p1 - p0).indexOf("\r");
                if (index >= 0) {
                    GlyphView v = (GlyphView) createFragment(p0, p0 + index + 1);
                    return v;
                }
            }
            catch (BadLocationException ex) {
                //should never happen
            }
        }
        return super.breakView(axis, p0, pos, len);
    }
}