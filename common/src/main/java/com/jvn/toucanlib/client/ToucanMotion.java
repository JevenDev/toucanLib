package com.jvn.toucanlib.client;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class toucanMotion {
    private toucanMotion() {
    }

    /**
     * Moves a value toward a target by at most maxStep.
     */
    public static float approach(float current, float target, float maxStep) {
        if (maxStep < 0.0F) {
            throw new IllegalArgumentException("maxStep must not be negative");
        }
        if (current < target) {
            return Math.min(current + maxStep, target);
        }
        return Math.max(current - maxStep, target);
    }

    /**
     * Moves a value toward a target by at most maxStep.
     */
    public static double approach(double current, double target, double maxStep) {
        if (maxStep < 0.0D) {
            throw new IllegalArgumentException("maxStep must not be negative");
        }
        if (current < target) {
            return Math.min(current + maxStep, target);
        }
        return Math.max(current - maxStep, target);
    }

    /**
     * Smooths a value with frame-rate-independent exponential decay.
     */
    public static float smoothExp(float current, float target, float responsiveness, float deltaTicks) {
        float alpha = expAlpha(responsiveness, deltaTicks);
        return Mth.lerp(alpha, current, target);
    }

    /**
     * Smooths a value with frame-rate-independent exponential decay.
     */
    public static double smoothExp(double current, double target, double responsiveness, float deltaTicks) {
        double alpha = expAlpha(responsiveness, deltaTicks);
        return Mth.lerp(alpha, current, target);
    }

    /**
     * Smooths a vector with frame-rate-independent exponential decay.
     */
    public static Vec3 smoothExp(Vec3 current, Vec3 target, float responsiveness, float deltaTicks) {
        return current.lerp(target, expAlpha(responsiveness, deltaTicks));
    }

    /**
     * Smooths an angle in degrees, wrapping around 180/-180 and limiting maximum movement per tick.
     */
    public static float smoothAngle(float current, float target, float responsiveness, float maxStepPerTick, float deltaTicks) {
        float delta = Mth.wrapDegrees(target - current);
        float alpha = expAlpha(responsiveness, deltaTicks);
        float step = delta * alpha;
        float maxStep = Math.max(0.0F, maxStepPerTick) * Math.max(0.0F, deltaTicks);
        return current + Mth.clamp(step, -maxStep, maxStep);
    }

    /**
     * Converts responsiveness and elapsed ticks into an interpolation alpha.
     */
    public static float expAlpha(float responsiveness, float deltaTicks) {
        if (responsiveness <= 0.0F || deltaTicks <= 0.0F) {
            return 0.0F;
        }
        return toucanEasing.clamp01(1.0F - (float) Math.exp(-responsiveness * deltaTicks));
    }

    /**
     * Converts responsiveness and elapsed ticks into an interpolation alpha.
     */
    public static double expAlpha(double responsiveness, double deltaTicks) {
        if (responsiveness <= 0.0D || deltaTicks <= 0.0D) {
            return 0.0D;
        }
        return Mth.clamp(1.0D - Math.exp(-responsiveness * deltaTicks), 0.0D, 1.0D);
    }

    /**
     * Returns a 0..1 looping phase from a time value and duration.
     */
    public static float loop(float time, float duration, float offset) {
        if (duration <= 0.0F) {
            return 0.0F;
        }
        float phase = (time + duration * offset) % duration;
        if (phase < 0.0F) {
            phase += duration;
        }
        return phase / duration;
    }

    /**
     * Returns a 0..1 sine oscillation from a time value and duration.
     */
    public static float oscillate(float time, float duration, float offset) {
        return 0.5F + 0.5F * Mth.sin(loop(time, duration, offset) * Mth.TWO_PI);
    }
}
