import { Route, Routes } from "react-router-dom";
import Shopping from "./Shopping";
import ShoppingDetail from "./shoppingDetil/ShoppingDetail";

function ShoppingForm() {
    return (
        <section>
            <Routes>
                <Route path="/" element={<Shopping/>}/>
                <Route path="/:id" element={<ShoppingDetail/>}/>
            </Routes>
        </section>
    )
}

export default ShoppingForm;