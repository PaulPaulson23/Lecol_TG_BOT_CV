package Utilities;

import java.util.HashMap;

public class GetEmptyProgressDataImpl implements GetEmptyProgressData<HashMap<Long, ProgressData>>{

    @Override
    public HashMap<Long, ProgressData> getEmptyVersion() {
        return new HashMap<>();
    }
}
