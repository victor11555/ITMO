"use strict"

function  Bag() {
    this.mset = new Map();

    this.add = function (elem){
       if(this.mset.has(elem)){
           this.mset.set(elem, this.mset.get(elem) + 1);
       }
       else{
           this.mset.set(elem, 1);
       }
    }

    this.remove = function (elem) {
        this.mset.delete(elem)
    }

    this.intersect = function (msetB) {
       let msetC = new Bag();
       let tmp = new Map(msetB.mset);
       for(let x of this.mset.keys()){

           if(tmp.has(x) && tmp.get(x) !== 0){
               msetC.add(x);
               tmp.set(x, tmp.get(x) - 1);
           }
       }
       return msetC;
    }

    this.union = function (msetB) {
        let msetC = new Bag();
        msetC.mset = new Map(this.mset);
        for(let x of msetB.mset.keys()){
            if(!msetC.mset.has(x)){
                msetC.mset.set(x, msetB.mset.get(x));
            }
            else {
                msetC.mset.set(x, msetC.mset.get(x)+ msetB.mset.get(x));
            }
        }
        return msetC;
    }

    this.equals = function (msetB){
        if(this.mset.size !== msetB.mset.size){
            console.log("bag's aren't equals");
            return false;
        }
        let processed_elem = 0;
        for (let x of msetB.mset.keys()){
            if(!this.mset.has(x) || this.mset.get(x) !== msetB.mset.get(x)){
                return false;
            }
            ++processed_elem;
        }
        return processed_elem === this.mset.size;
    }

    this.toString = function () {
       let str = "";

       for(let data of this.mset.entries()){
           str += data[0] + ": " + data[1] + ", "
       }
       return  str.substr(0, str.length-2);
    }
}
let bag1 = new Bag();
bag1.add(4);
bag1.add(3);
bag1.add(2);
bag1.add(1);
console.log(bag1.toString());

let bag2 = new Bag();
bag2.add(2);
bag2.add(3);
bag2.add(4);
console.log(bag2.toString());


console.log(bag1.equals(bag2));
