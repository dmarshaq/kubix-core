package mathj;

public class RectComponent extends Rect {
    private Vector3f reference;

    public RectComponent() {
        super();
        this.reference = new Vector3f();
    }
/*
//    public RectComponent(float x, float y, float width, float height, float z) {
//        super(x, y, width, height, z);
//        this.reference = new Vector3f();
//    }
//
//    public RectComponent(Vector3f pos, float width, float height) {
//        super(pos, width, height);
//        this.reference = new Vector3f();
//    }
//
//    public RectComponent(float x, float y, float width, float height, float z, Vector3f reference) {
//        super(x, y, width, height, z);
//        this.reference = reference;
//    }
//
//    public RectComponent(Vector3f pos, float width, float height, Vector3f reference) {
//        super(pos, width, height);
//        this.reference = reference;
//    }
*/
    public RectComponent(Rect rect, Vector3f reference) {
        super(rect);
        this.reference = reference;
    }

    public RectComponent(RectComponent rect) {
        super.setPosition(rect.getPositionObject());
        this.width = rect.width;
        this.height = rect.height;
        this.reference = rect.getReferencePosition();
    }

    @Override
    public Vector3f getPosition() {
        return MathJ.sum2d(super.getPositionObject(), reference);
    }

    @Override
    public Vector3f getCenter() {
        return MathJ.sum2d(super.getCenter(), reference);
    }

    public void setReferencePosition(Vector3f refPos) {
        reference = refPos;
    }

    public Vector3f getReferencePosition() {
        return reference;
    }

    @Override
    public String toString() {
        return "at: " + super.getPositionObject() + " width: " + width + " height: " + height + " refPos: " + reference;
    }


}
