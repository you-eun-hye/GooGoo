document.addEventListener('DOMContentLoaded', function() {
    var request = $.ajax({
        url: "/calendar",
        method: "GET",
        dataType: "json"
    });

    request.done(function(data){
        console.log(data);

        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            height: 1300,
            initialView: 'dayGridMonth',
            events: data
        });
        calendar.render();
    });
    request.fail(function(jqXHR, textStatus){
        alert("Request Failed: " + textStatus);
    });
});