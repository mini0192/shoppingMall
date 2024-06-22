import { BrowserRouter, Route, Routes } from "react-router-dom";

import Home from "../pages/Home";
import ShoppingForm from "../pages/shopping/ShoppingForm";
import MemberForm from "../pages/member/MemberForm";
import Header from "./Header";
import AdminForm from "../pages/admin/AdminForm";

function Page() {
    return (
        <>
        <BrowserRouter>
            <Header/>
            <div className='marginObj'>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/shopping/*" element={<ShoppingForm/>}/>
                    <Route path="/member/*" element={<MemberForm/>}/>
                    <Route path="/admin/*" element={<AdminForm/>}/>
                </Routes>
            </div>
        </BrowserRouter>
        </>
    )
}

export default Page;