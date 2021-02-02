package neural_network.graph;

import java.util.Objects;

public final class GraphNode {
    private final String name;
    private final int age;

    public GraphNode(String name, int age) {
        this.name = Objects.requireNonNull(name, "name");
        this.age = age;
    }

    public String name() {
        return name;
    }

    public int age() {
        return age;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof GraphNode) {
            GraphNode that = (GraphNode) other;
            return this.name.equals(that.name)
                    && this.age == that.age;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "(" + name + ", " + age + ")";
    }
}