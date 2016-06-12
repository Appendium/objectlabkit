package net.objectlab.kit.pf;

@FunctionalInterface
public interface ValidationEngine {
    ValidationResults validate(ExistingPortfolio portfolio);
}
