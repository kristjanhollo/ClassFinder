package util;

public class FullName implements Comparable<FullName> {
    private final String packageName;
    private final String className;


    public FullName(String fullClassName) {
        int indexOfLastDOt = fullClassName.lastIndexOf(".");
        if (indexOfLastDOt == -1) {
            packageName = "";
            className = fullClassName;
        } else {
            packageName = fullClassName.substring(0, indexOfLastDOt);
            className = fullClassName.substring(indexOfLastDOt + 1);
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
