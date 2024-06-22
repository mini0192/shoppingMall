import { useEffect, useState } from "react";
import Comment from "./Comment";
import Content from "./Content";
import Image from "./Image";
import axios from "axios";
import { useParams } from "react-router-dom";
import Url from "../../../config/backendConnect";

function ShoppingDetail() {
    const [item, setItem] = useState({})
    const {id} = useParams();

    useEffect(() => {
        axios.get(`${Url}/item/${id}`)
        .then(res => {
            setItem(res.data)
        })
        .catch(err => {
            console.log(err.response.data.error)
        })
    },[])

    return (
        <section>
            <div className="container text-center">
                <div className="row">
                    <div className="col">
                        <Image data={ item }/>
                    </div>
                    <div className="col">
                        <Content data={ item }/>
                    </div>
                </div>
            </div>
            <Comment/>
        </section>
    )
}

export default ShoppingDetail;