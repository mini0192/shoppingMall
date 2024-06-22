import { Route, Routes } from "react-router-dom";
import AddItem from "./AddItem";
import AdminHeader from "./AdminHeader";
import ItemList from "./ItemList";
import MemberList from "./MemberList";

function AdminForm() {
    return (
        <section>
            <AdminHeader/>
            <div>
                <Routes>
                    <Route path="/itemList" element={<ItemList/>}/>
                    <Route path="/itemAdd" element={<AddItem/>}/>
                    <Route path="/memberList" element={<MemberList/>}/>
                </Routes>
            </div>
        </section>
    )
}

export default AdminForm;