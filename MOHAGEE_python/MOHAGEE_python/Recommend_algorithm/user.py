class user:
    def __init__(self, location, gender, age ,filters):
        self.location = location
        self.gender = gender
        self.age = age
        self.filter = filters
    def get_location(self):
        return self.location

    def get_gender(self):
        return self.gender

    def get_age(self):
        return self.age

    def get_filters(self):
        return self.filter

