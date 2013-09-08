function unhide(div_to_hide_or_unhide) {
// get the given item and hide it, if it is unhidden, and unhide, otherwise
var item = document.getElementById(div_to_hide_or_unhide);
    if (item) 
           item.className=(item.className=='hidden')?'unhidden':'hidden';  
}
