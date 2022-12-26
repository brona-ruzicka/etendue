package com.brona.etendue.rev1.math.simple;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class Tuple3 implements Point3, Vector3 {

    float x, y, z;


    @NotNull
    public static Tuple3 create(float x, float y, float z) {
        return new Tuple3(x, y, z);
    }


    @NotNull
    @Override
    public Vector3 toVector() {
        return this;
    }

    @NotNull
    @Override
    public Tuple3 toTuple() { return this; }

    @NotNull
    @Override
    public Point3 toPoint() {
        return this;
    }


    @NotNull
    @Override
    public Tuple2 euclidean() {
        return new Tuple2(this.x / this.z, this.y / this.z);
    }



    @Override
    public float squaredLength() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    @Override
    public float length() {
         return (float) Math.sqrt(squaredLength());
    }

    @NotNull
    @Override
    public Tuple3 normalize() {
        float length = this.length();
        return new Tuple3(this.x / length, this.y / length, this.z / length);
    }

    @NotNull
    @Override
    public Tuple3 invert() {
        return new Tuple3(-this.x, -this.y, -this.z);
    }

    @NotNull
    @Override
    public Tuple3 plus(@NotNull Vector3 vector) {
        return new Tuple3(this.x + vector.getX(), this.y + vector.getY(), this.z + vector.getZ());
    }

    @NotNull
    @Override
    public Tuple3 minus(@NotNull Point3 point) {
        return minus((Base3) point);
    }

    @NotNull
    @Override
    public Tuple3 minus(@NotNull Vector3 vector) {
        return minus((Base3) vector);
    }

    @NotNull
    public Tuple3 minus(@NotNull Base3 base) {
        return new Tuple3(this.x - base.getX(), this.y - base.getY(), this.z - base.getZ());
    }

    @NotNull
    @Override
    public Tuple3 times(float a) {
        return new Tuple3(a * this.x, a * this.y, a * this.z);
    }

    @Override
    public float dot(@NotNull Vector3 vector) {
        return this.x * vector.getX() + this.y * vector.getY() + this.z * vector.getZ();
    }

    @NotNull
    @Override
    public Tuple3 cross(@NotNull Vector3 vector) {
        return new Tuple3(
                this.y * vector.getZ() - vector.getY() * this.z,
                this.z * vector.getX() - vector.getZ() * this.x,
                this.x * vector.getY() - vector.getX() * this.y
        );
    }

}
