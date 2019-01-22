package io.github.contextawareness.core.ProviderAnd_Or;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.github.contextawareness.core.Callback;
import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.purposes.Purpose;

public class EnvironmentAndProvider extends PStreamProvider {
    // 用于存储联立返回事件的单个因子；
    private class StateFactor {
        Boolean isNew;
        Item item;

        StateFactor(Item item, Boolean isNew) {
            this.item = item;
            this.isNew = isNew;
        }
    }

    private LinkedList<EnvironmentFactor> factors;
    private Purpose purpose;
    private HashMap<String, StateFactor> stateFactorsMap; // 用于标记返回数据的新旧, key 为 Item 的多态子类名，true 为新，false 为旧；

    public EnvironmentAndProvider(LinkedList<EnvironmentFactor> factors){
        this.factors = factors;
        this.purpose = Purpose.UTILITY("EnvironmentAndCommon");
        this.stateFactorsMap = new HashMap<>();
    }

    @Override
    protected void provide() {
        final EnvironmentAndProvider innerThis = this;
        Callback<Item> callback = new Callback<Item>() {
            @Override
            protected void onInput(Item input) {
                innerThis.stateFactorsMap.put(input.getClass().toString(), new StateFactor(input, true));
                innerThis.checkAndOutput();
            }
        };

        for (EnvironmentFactor factor : this.factors) {
            PStream p = this.getUQI().getData(factor.provider, this.purpose);
            for (String field : factor.fieldMap.keySet()) {
                p.setField(field, factor.fieldMap.get(field)).forEach(callback);
            }
        }
    }

    private void checkAndOutput() {
        if (this.stateFactorsMap.size() == this.factors.size()) { // 只有相等情况，才有可能在 And 情况下出发返回
            boolean needOutput = true;
            for (StateFactor tmp : this.stateFactorsMap.values()) {
                needOutput = tmp.isNew;
                if (needOutput == false) {
                    return;
                }
            }

            if (needOutput == true) {
                this.output(this.reConstructStateMapItems());
                // 分发一次之后标记数据为旧数据
                for (String str : this.stateFactorsMap.keySet()) {
                    StateFactor factor = this.stateFactorsMap.get(str);
                    factor.isNew = false;
                    this.stateFactorsMap.put(str,factor);
                }
            }
        }
    }

    private Item reConstructStateMapItems() {
        Item tmp = new Item();
        for (StateFactor factor : this.stateFactorsMap.values()) {
            tmp.joinItem(factor.item);
        }
        return tmp;
    }
}
