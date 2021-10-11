package util;

public class FullName implements Comparable<FullName> {
    private final String packageName;
    private final String className;


    public FullName(String fullClassName) {
        int indexOfLastDot = fullClassName.lastIndexOf(".");
        if (indexOfLastDot == -1) {
            packageName = "";
            className = fullClassName;
        } else {
            packageName = fullClassName.substring(0, indexOfLastDot);
            className = fullClassName.substring(indexOfLastDot + 1);
        }
    }

    @Override
    public String toString() {
        return packageName.length() > 0 ? String.format("%s.%s", packageName, className) : className;
    }

    @Override
    public int compareTo(FullName another) {
        return this.className.compareTo(another.className);
    }
}
