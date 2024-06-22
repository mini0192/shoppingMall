import { Link } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import ReplaceMouny from "../../config/ReplaceMouny";
import Url from "../../config/backendConnect";
import PageNumberConfig from "../../config/PageNumberConfig";

function Shopping() {

    const DEFULTCOUNT = 4
    let pageNumber = 1;

    const [count, setCount] = useState(DEFULTCOUNT)
    const [item, setItem] = useState([])
    const [pageData, setPageData] = useState({})

    const [dropInfo, setDropInfo] = useState(DEFULTCOUNT)



    const getAPI = (count, page, option) => {
        axios.get(`${Url}/item?count=${count}&page=${page}`)
        .then(res => {
            pageNumber = res.data.pageable?.pageNumber + 1;
            setPageData(res.data)
            setItem(res.data.content)
        })
        .catch(err => {
            console.log(err.response.data.error)
        })
    }

    useEffect(() => {
        getAPI(DEFULTCOUNT, pageNumber)
    },[])

    function dropdownFunc(number) {
        return (
            <li className="dropdown-item" onClick={ () => { getAPI(number, pageNumber, ""); setDropInfo(number); setCount(number);} }>{number}개씩 보기</li>
        )
    }

    return (
        <section>
            <div className="marginTopObj"style={{textAlign: "center"}}>
                <div className="row marginTopObj">
                    <div className="col"></div>
                        <div className="col-6">
                            <h3 style={{display: "inline"}}>제품</h3>
                        </div>
                        <div className="col">
                        <div className="btn-group marginObj" style={{display: "inline", textAlign: "right"}}>
                            <button type="button" className="btn btn-info dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                { `${dropInfo}개씩 보기` }
                            </button>
                            <ul className="dropdown-menu">
                                { dropdownFunc(DEFULTCOUNT) }
                                { dropdownFunc(DEFULTCOUNT * 2) }
                                { dropdownFunc(DEFULTCOUNT * 3) }
                            </ul>
                        </div>
                    </div>
                </div>
                <hr/>
                <div className="marginTopObj marginTopObj" style={{textAlign: "left"}}>
                { item.map(data => (
                    <Link key={ data.id } to={`/shopping/${data.id}`} className="linkObj pagginObj" style={{display: "inline-block", width: "25%"}}>
                        <figure className="figure hoverSizeUpObj" style={{textAlign: "center"}}>
                            <img src={ `${Url}/${data.imageList[0]}` } className="figure-img img-fluid rounded" width="90%"/>
                            <h4>{data.name}</h4>
                            <figcaption className="figure-caption">
                                { ReplaceMouny(data.price) }
                            </figcaption>
                        </figure>
                    </Link>
                ))}
                </div>
                { PageNumberConfig(pageData, count, "", getAPI) }
            </div>
        </section>
    )
}

export default Shopping;