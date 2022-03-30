import axios from "axios"
import { useEffect, useState } from "react"
import { useLocation } from "react-router"
import { toast } from "react-toastify"
import FoodItem from "../../../components/FoodItems/foodItem"
import Header1 from "../../../components/Header1/header1"
import { URL } from "../../../config"
import "./cuisineFood.css"

const CuisineFood = () => {
    const { state } = useLocation()
    const [foodItems, setFoodItems] = useState([])



    const function1 = () => {
        const { id } = state
        const url = `${URL}/user/fooditems/${id}`
        console.log(id)
        axios.get(url).then((response) => {
            const result = response.data
            if (result.status === 'success') {
                setFoodItems(result.data)
            } else {
                console.log(result.error)
                toast.error(result['error'])
            }
        })
    }

    useEffect(() => {
        function1()
    }, [])
    return (<div className="container">
        <Header1></Header1>
        <div className="cuisine-tabs">

        </div>
        <div className="food-items">
            {foodItems.map((foodItem) => {
                return <FoodItem foodItem={foodItem}></FoodItem>
            })}
        </div>
    </div>
    )
}

export default CuisineFood