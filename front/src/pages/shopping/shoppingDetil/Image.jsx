import Url from "../../../config/backendConnect";

function Image(props) {
    const imageList = props.data.imageList
    return (
        <div id="carouselExample" className="carousel slide">
            <div className="carousel-inner">
                {
                    imageList?.map(e => (
                        <div className="carousel-item active" key={e}>
                            <img src={ `${Url}/${e}` } className="d-block w-100"/>
                        </div>
                    ))
                }
            </div>
            <button className="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Previous</span>
            </button>
            <button className="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Next</span>
            </button>
        </div>
    )
}

export default Image;