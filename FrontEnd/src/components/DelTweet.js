import React, {Component} from 'react';
import axios from 'axios';

export default class DelTweet extends Component {

  constructor(props) {
    super(props);
        this.onDeleteTweet = this.onDeleteTweet.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.state = {
            deltweet: '',
        }
    }

    onDeleteTweet(e) {
        this.setState({
            deltweet: e.target.value
        });
    }
    
    onSubmit(e) {
        e.preventDefault();
        let formData = new FormData(e.target);
        formData.append('id', this.state.deltweet);   

        axios.delete("http://132.145.52.5:8080/tweet", { params: { id: this.state.deltweet } }).then(response => {
            console.log(response);
            alert("Please check Twitter. Your tweet with ID: '" + this.state.deltweet + " ' was deleted")
        });
    }

    render(){
        return(
        <div>
            <form onSubmit={this.onSubmit}>
                <div className="form-group">
                    <label>Enter ID for deleting a tweet: </label>
                    <input  type="text"
                            name = "deltweet"
                            className="form-control"
                            value={this.state.deltweet}
                            onChange={this.onDeleteTweet}
                            />
                </div>
                <div className="form-group">
                    <input type="submit" value="Delete Tweet" className="btn btn-primary" />
                </div>
            </form>
        </div>
        )
    }
}
