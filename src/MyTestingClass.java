public class MyTestingClass {
    private final int id;
    private final String name;
    private final int group;

    public MyTestingClass(int id, String name, int group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + group;
        for (int i = 0; i < name.length(); i++) {
            result = 31 * result + name.charAt(i);
        }
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MyTestingClass)) {
            return false;
        }

        MyTestingClass other = (MyTestingClass) object;
        return id == other.id && group == other.group && name.equals(other.name);
    }

    @Override
    public String toString() {
        return "MyTestingClass{id=" + id + ", name='" + name + "', group=" + group + "}";
    }
}
