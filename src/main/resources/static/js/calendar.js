// document.addEventListener('DOMContentLoaded', function() {
//     var calendarEl = document.getElementById('calendar');
//     var calendar = new FullCalendar.Calendar(calendarEl, {
//         height: 1300,
//         initialView: 'dayGridMonth',
//         editable: false,
//         events: [
//             {
//                 title: '200초코',
//                 start: '2023-07-05'
//             },
//             {
//                 title: '200초코',
//                 start: '2023-05-12'
//             }
//         ],
//         eventClick: function(arg){
//             alert("babo");
//         }
//     });
//     calendar.render();
// });

// document.addEventListener('DOMContentLoaded', function() {
//     $(function (){
//         var request = $.ajax({
//             url: "/calendar",
//             method: "GET",
//             dataType: "json"
//         });
//         request.done(function(data){
//             console.log(data);
//
//             var calendarEl = document.getElementById('calendar');
//             var calendar = new FullCalendar.Calendar(calendarEl, {
//                 height: 1300,
//                 initialView: 'dayGridMonth',
//                 editable: false,
//                 selectable: false,
//                 events: data,
//                 eventClick: function(arg){
//                     console.log(arg.event);
//                 }
//             });
//             calendar.render();
//         });
//         request.fail(function(jqXHR, textStatus){
//             alert("Request Failed: " + textStatus);
//         });
//     });
// });