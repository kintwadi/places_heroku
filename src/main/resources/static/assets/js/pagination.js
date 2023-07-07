// pagination 

const numberPerPage = 4;
var pageNumber = 1;
var numberOfPages = 30;
const postsSection = document.querySelector(".posts-section");
function fetchProperties (pageNumber){
	var URL = "/properties?pageNumber="+pageNumber+"&numberPerPage="+numberPerPage;
    $.get(URL, function(cards, status){
    	
        postsSection.innerHTML = "";
        cards.forEach((card) => {
            postsSection.innerHTML += `
               <div class="box-two proerty-item">
                    <div class="item-thumb">
                       <a href="${card.page}" >
                         <img src="${card.image}" >
                       </a>
                   </div>
                  <div class="item-entry overflow">
                       <h5><a href="#" ><span >${card.name}</span> </a></h5>
                       <div class="dot-hr"></div>
                       <span class="pull-left"><b><span >${card.areaText}</span> :</b> <span >${card.area}</span> </span>
                       <span  class="proerty-price pull-right">${card.reason}</span><br>
                       <a href="${card.page}" style="float: right"><i class="fa fa-external-link" aria-hidden="true"></i></a>
                  </div>
           </div> 
          `;
        });
    	   
     });
    	
  }

fetchProperties(pageNumber)


// Add event listeners to the prev button
const prev = document.querySelector('.prev');
prev.addEventListener('click', (e) => {
   e.preventDefault();
   if (pageNumber > 1) {
      pageNumber--;
      fetchProperties(pageNumber)
   }
});

// Add event listeners to the next button
const next = document.querySelector(".next");
next.addEventListener("click", (e) => {
    e.preventDefault();
    if (pageNumber < numberOfPages) {
        pageNumber++;
        fetchProperties(pageNumber)
    }
});
   