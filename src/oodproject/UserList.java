package oodproject;

import java.util.ArrayList;

public class UserList implements Container {
    public static ArrayList<String> userNames = new ArrayList<String>();

    @Override
    public Iterator getIterator() {
        return new UserListIterator();
    }

    private class UserListIterator implements Iterator {
        int index;

        public boolean hasNext() {
            if (index < userNames.size()) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return userNames.get(index++);
            }
            return null;
        }
    }
}